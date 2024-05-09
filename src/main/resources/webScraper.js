const fs = require('fs');
const cheerio = require('cheerio');
const fetch = require('node-fetch');

const tablesData = [];

const daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

const badmintonCourts = {
    "Richcraft Recreation Complex-Kanata": {
        "href": "https://ottawa.ca/en/recreation-and-parks/facilities/place-listing/richcraft-recreation-complex-kanata#", "booking": "https://reservation.frontdesksuite.ca/rcfs/richcraftkanata/Home/Index?Culture=en&PageId=b3b9b36f-8401-466d-b4c4-19eb5547b43a&ShouldStartReserveTimeFlow=False&ButtonId=00000000-0000-0000-0000-000000000000"
    },
    "Nepean Sportsplex": {
        "href": "https://ottawa.ca/en/recreation-and-parks/facilities/place-listing/nepean-sportsplex#", "booking": "https://reservation.frontdesksuite.ca/rcfs/nepeansportsplex/Home/Index?Culture=en&PageId=b0d362a1-ba36-42ae-b1e0-feefaf43fe4c&ShouldStartReserveTimeFlow=False&ButtonId=00000000-0000-0000-0000-000000000000"
    }
};

const parsePages = async () => {
    for (const court in badmintonCourts) {
        try {
            const scheduleResponse = await fetch(badmintonCourts[court].href);
            const scheduleHtml = await scheduleResponse.text();
            const $schedule = cheerio.load(scheduleHtml);
            const scheduleTables = $schedule('table');

            const bookingResponse = await fetch(badmintonCourts[court].booking);
            const bookingHtml = await bookingResponse.text();
            const $booking = cheerio.load(bookingHtml);
            const bookingDivs = $booking('div.content');

            scheduleTables.each((index, scheduleTable) => {
                const caption = $schedule(scheduleTable).find('caption').text();
                if (caption.toLowerCase().includes('racquet')) {
                    const tableData = { courtName: court, href: badmintonCourts[court].booking, table: {} };
                    const $tbody = $schedule(scheduleTable).find('tbody');
                    const $tr = $tbody.find('tr');
                    $tr.each((index, tr) => {
                        const $th = $schedule(tr).find('th');
                        $th.each((index, th) => {
                            const activity = $schedule(th).text().trim();
                            if (/badminton/i.test(activity)) {
                                const rowData = {};
                                const $td = $schedule(tr).find('td');
                                bookingDivs.each((index, bookingDiv) => {
                                    const bookingText = $booking(bookingDiv).text().trim();
                                    if(activity.replace(/[^a-zA-Z\s]/g, '') === bookingText.replace(/[^a-zA-Z\s]/g, '')) {
                                        rowData["link"] = $booking(bookingDiv).parent().attr('href')
                                    }
                                });                                
                                $td.each((index, td) => {
                                    const day = daysOfWeek[index];
                                    let cellText = $schedule(td).text().replace(/[\n\t]/g, '').trim();

                                    if (cellText !== 'n/a') {
                                        const timeSlots = cellText.split(',').map(slot => slot.trim());
                                        const adjustedTimeSlots1 = timeSlots.map(slot => slot.replace(/\bNoon\b/g, '12').replace(/\b11 am - noon\b/g, '11 - 12 pm').replace(/\b11:30 am - 12:30 pm\b/g, '11:30 - 12:30 pm'));
                                        const adjustedTimeSlots2 = adjustedTimeSlots1.map(slot => {
                                            return slot.replace(/\b(?!(?:30\b))(\d+)\b(?!(?::|30\b))/g, '$1:00');
                                        });
                                        rowData[day] = adjustedTimeSlots2;
                                    }                                    
                                });
                                tableData.table[activity] = rowData;
                            }
                        });
                    });
                    tablesData.push(tableData);
                }
            });
        } catch (error) {
            console.error(error);
        }
    }

    const jsonData = JSON.stringify(tablesData, null, 2);
    fs.writeFile('data.json', jsonData, (err) => {});
};

parsePages();
