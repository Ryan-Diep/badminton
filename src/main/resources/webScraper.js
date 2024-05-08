const fs = require('fs');
const cheerio = require('cheerio');
const fetch = require('node-fetch');

const tablesData = [];

const daysOfWeek = ['Sat', 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri'];

const badmintonCourts = {
    "Richcraft Recreation Complex-Kanata": {
        "href": "https://ottawa.ca/en/recreation-and-parks/facilities/place-listing/richcraft-recreation-complex-kanata#",
    },
    "Nepean Sportsplex": {
        "href": "https://ottawa.ca/en/recreation-and-parks/facilities/place-listing/nepean-sportsplex#",
    }
};

const parsePages = async () => {
    for (const court in badmintonCourts) {
        try {
            const response = await fetch(badmintonCourts[court].href);
            const html = await response.text();
            const $ = cheerio.load(html);
            const tables = $('table');

            tables.each((index, table) => {
                const caption = $(table).find('caption').text();
                if (caption.toLowerCase().includes('racquet')) {
                    const tableData = { courtName: court, table: {} };
                    const $tbody = $(table).find('tbody');
                    const $tr = $tbody.find('tr');
                    $tr.each((index, tr) => {
                        const $th = $(tr).find('th'); 
                        $th.each((index, th) => {
                            if (/badminton/i.test($(th).text().trim())) {
                                const rowData = {};
                                const $td = $(tr).find('td');
                                $td.each((index, td) => {
                                    const day = daysOfWeek[index];
                                    let cellText = $(td).text().replace(/[\n\t]/g, '').trim();
                                    if (cellText !== 'n/a') {
                                        const timeSlots = cellText.split(',').map(slot => slot.trim());
                                        const adjustedTimeSlots1 = timeSlots.map(slot => slot.replace(/\bNoon\b/g, '12').replace(/\b11 am - noon\b/g, '11 - 12 pm'));
                                        const adjustedTimeSlots2 = adjustedTimeSlots1.map(slot => {
                                            return slot.replace(/\b(\d+)\s+(?![^:]*:)/g, '$1:00 ');
                                        });
                                        rowData[day] = adjustedTimeSlots2;
                                    }
                                });
                                tableData.table[$(th).text().trim()] = rowData;
                            }
                        });
                    });
                    tablesData.push(tableData);
                }
            });
        } 
        catch (error) {
            console.error(error);
        }
    }
    
    const jsonData = JSON.stringify(tablesData, null, 2);
    fs.writeFile('data.json', jsonData, (err) => {});
};

parsePages();
