var pureJSCalendar = (function () {
    let wrap, label, calYear, calMonth, calDateFormat, firstDay, isIE11;
    isIE11 = !!window.MSInputMethodContext && !!document.documentMode;
    if (window.months === undefined) {
        window.months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    }
    if (window.shortDays === undefined) {
        window.shortDays = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];
    }
    const firstDayCombinations = [[0, 1, 2, 3, 4, 5, 6], [1, 2, 3, 4, 5, 6, 0], [2, 3, 4, 5, 6, 0, 1], [3, 4, 5, 6, 0, 1, 2], [4, 5, 6, 0, 1, 2, 3], [5, 6, 0, 1, 2, 3, 4], [6, 0, 1, 2, 3, 4, 5]]
    const DOMstrings = {
        divCal: 'cal',
        divCalQ: '#cal',
        monthLabel: 'label',
        btnPrev: 'prev',
        btnNext: 'next',
        sunLabel: 'eformSun',
        monLabel: 'eformMon',
        tueLabel: 'eformTue',
        wedLabel: 'eformWed',
        thuLabel: 'eformThu',
        friLabel: 'eformFri',
        satLabel: 'eformSat',
        tdDay: '.eformDay'
    }

    function open(dateFormat, x, y, firstDayOfWeek, minDate, maxDate, element, zindex) {
        if (document.getElementById('cal')) {
            return false;
        }
        eFormMinimalDate = DateParse(minDate);
        eFormMaximalDate = DateParse(maxDate);
        eFormCalendarElement = element;
        firstDay = firstDayOfWeek;
        if (firstDayOfWeek === undefined) {
            firstDayOfWeek = 6;
        } else {
            firstDayOfWeek -= 1;
        }
        const newHtml = '<div id="cal" style="top:' + y + 'px;left:' + x + 'px;z-index:' + zindex + ';"><div class="header"><span class="left button" id="prev"> &lang; </span><span class="left hook"></span><span class="month-year" id="label"> June 20&0 </span><span class="right hook"></span><span class="right button" id="next"> &rang; </span></div ><table id="days"><tr><td id="eformSun">sun</td><td id="eformMon">mon</td><td id="eformTue">tue</td><td id="eformWed">wed</td><td id="eformThu">thu</td><td id="eformFri">fri</td><td id="eformSat">sat</td></tr></table><div id="cal-frame"><table class="curr"><tbody></tbody></table></div></div >'
        document.getElementsByTagName('body')[0].insertAdjacentHTML('beforeend', newHtml);
        calDateFormat = dateFormat;
        wrap = document.getElementById(DOMstrings.divCal);
        label = document.getElementById(DOMstrings.monthLabel);
        document.getElementById(DOMstrings.btnPrev).addEventListener('click', function () {
            switchMonth(false);
        });
        document.getElementById(DOMstrings.btnNext).addEventListener('click', function () {
            switchMonth(true);
        });
        label.addEventListener('click', function () {
            switchMonth(null, new Date().getMonth(), new Date().getFullYear());
        });
        const dayCombination = firstDayCombinations[firstDayOfWeek];
        document.getElementById(DOMstrings.sunLabel).textContent = window.shortDays[dayCombination[0]];
        document.getElementById(DOMstrings.monLabel).textContent = window.shortDays[dayCombination[1]];
        document.getElementById(DOMstrings.tueLabel).textContent = window.shortDays[dayCombination[2]];
        document.getElementById(DOMstrings.wedLabel).textContent = window.shortDays[dayCombination[3]];
        document.getElementById(DOMstrings.thuLabel).textContent = window.shortDays[dayCombination[4]];
        document.getElementById(DOMstrings.friLabel).textContent = window.shortDays[dayCombination[5]];
        document.getElementById(DOMstrings.satLabel).textContent = window.shortDays[dayCombination[6]];
        label.click();
    }

    function switchMonth(next, month, year) {
        var curr = label.textContent.trim().split(' '), calendar, tempYear = parseInt(curr[1], 10);
        month = month || ((next) ? ((curr[0] === window.months[11]) ? 0 : months.indexOf(curr[0]) + 1) : ((curr[0] === window.months[0]) ? 11 : months.indexOf(curr[0]) - 1));
        if (!year) {
            if (next && month === 0) {
                year = tempYear + 1;
            } else if (!next && month === 11) {
                year = tempYear - 1;
            } else {
                year = tempYear;
            }
        }
        calMonth = month + 1;
        calYear = year;
        calendar = createCal(year, month);
        var curr = document.querySelector('.curr')
        curr.innerHTML = '';
        curr.appendChild(calendar.calendar());
        if (eFormMinimalDate !== undefined) {
            if (year < eFormMinimalDate.year || year <= eFormMinimalDate.year && month <= eFormMinimalDate.month - 1) {
                const emptyCount = document.querySelector('.curr table').rows[0].querySelectorAll('td:empty').length;
                const tdDisabled = document.querySelectorAll('.eformDay');
                for (var i = 0; i < tdDisabled.length; ++i) {
                    if (i - emptyCount + 1 < eFormMinimalDate.day || month < eFormMinimalDate.month - 1 || year < eFormMinimalDate.year) {
                        tdDisabled[i].classList.add('eformDayDisabled');
                        tdDisabled[i].onclick = function () {
                            return false;
                        }
                    }
                }
            }
        }
        if (eFormMaximalDate !== undefined) {
            if (year > eFormMinimalDate.year || year >= eFormMaximalDate.year && month >= eFormMaximalDate.month - 1) {
                const emptyCount = document.querySelector('.curr table').rows[0].querySelectorAll('td:empty').length;
                const tdDisabled = document.querySelectorAll('.eformDay');
                for (var i = 0; i < tdDisabled.length; ++i) {
                    if (i - emptyCount + 1 > eFormMaximalDate.day || month > eFormMaximalDate.month - 1 || year > eFormMaximalDate.year) {
                        tdDisabled[i].classList.add('eformDayDisabled');
                        tdDisabled[i].onclick = function () {
                            return false;
                        }
                    }
                }
            }
        }
        label.textContent = calendar.label;
    }

    function createCal(year, month) {
        var day = 1, i, j, haveDays = true, startDay = new Date(year, month, day).getDay(),
            daysInMonths = [31, (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
            calendar = [];
        startDay -= firstDay;
        if (startDay < 0) {
            startDay = 7 + startDay;
        }
        if (createCal.cache[year] && !isIE11) {
            if (createCal.cache[year][month]) {
                return createCal.cache[year][month];
            }
        } else {
            createCal.cache[year] = {};
        }
        i = 0;
        while (haveDays) {
            calendar[i] = [];
            for (j = 0; j < 7; j++) {
                if (i === 0) {
                    if (j === startDay) {
                        calendar[i][j] = day++;
                        startDay++;
                    }
                } else if (day <= daysInMonths[month]) {
                    calendar[i][j] = day++;
                } else {
                    calendar[i][j] = '';
                    haveDays = false;
                }
                if (day > daysInMonths[month]) {
                    haveDays = false;
                }
            }
            i++;
        }
        for (i = 0; i < calendar.length; i++) {
            calendar[i] = '<tr><td class="eformDay" onclick="pureJSCalendar.dayClick(this)">' + calendar[i].join('</td><td class="eformDay" onclick="pureJSCalendar.dayClick(this)">') + '</td></tr>';
        }
        const calendarInnerHtml = calendar.join('');
        calendar = document.createElement('table', {class: 'curr'});
        calendar.innerHTML = calendarInnerHtml;
        const tdEmty = calendar.querySelectorAll('td:empty');
        for (var i = 0; i < tdEmty.length; ++i) {
            tdEmty[i].classList.add('nil');
        }
        if (month === new Date().getMonth()) {
            const calTd = calendar.querySelectorAll('td');
            const calTdArray = Array.prototype.slice.call(calTd);
            calTdArray.forEach(function (current, index, array) {
                if (current.innerHTML === new Date().getDate().toString()) {
                    current.classList.add('today');
                }
            });
        }
        createCal.cache[year][month] = {
            calendar: function () {
                return calendar
            }, label: months[month] + ' ' + year
        };
        return createCal.cache[year][month];
    }

    createCal.cache = {};
    const dayClick = function (element) {
        const dateResult = DateToString(new Date(calYear, calMonth - 1, parseInt(element.innerHTML)), calDateFormat);
        document.getElementById(eFormCalendarElement).value = dateResult;
        close();
    }

    function joinObj(obj, seperator) {
        var out = [];
        for (k in obj) {
            out.push(k);
        }
        return out.join(seperator);
    }

    function DateToString(inDate, formatString) {
        var dateObject = {
            M: inDate.getMonth() + 1,
            d: inDate.getDate(),
            D: inDate.getDate(),
            h: inDate.getHours(),
            m: inDate.getMinutes(),
            s: inDate.getSeconds(),
            y: inDate.getFullYear(),
            Y: inDate.getFullYear()
        };
        var dateMatchRegex = joinObj(dateObject, "+|") + "+";
        var regEx = new RegExp(dateMatchRegex, "g");
        formatString = formatString.replace(regEx, function (formatToken) {
            var datePartValue = dateObject[formatToken.slice(-1)];
            var tokenLength = formatToken.length;
            if (formatToken.indexOf('y') < 0 && formatToken.indexOf('Y') < 0) {
                var tokenLength = Math.max(formatToken.length, datePartValue.toString().length);
            }
            var zeroPad;
            try {
                zeroPad = (datePartValue.toString().length < formatToken.length ? "0".repeat(tokenLength) : "");
            } catch (ex) {
                zeroPad = (datePartValue.toString().length < formatToken.length ? repeatStringNumTimes("0", tokenLength) : "");
            }
            return (zeroPad + datePartValue).slice(-tokenLength);
        });
        return formatString;
    }

    Date.prototype.ToString = function (formatStr) {
        return DateToString(this.toDateString(), formatStr);
    }
    function repeatStringNumTimes(string, times) {
        var repeatedString = "";
        while (times > 0) {
            repeatedString += string;
            times--;
        }
        return repeatedString;
    }

    function close() {
        //fadeOutEffect(DOMstrings.divCalQ, remove);
    }

    var remove = function () {
        try {
            document.getElementById(DOMstrings.divCal).remove();
        } catch (ex) {
            const child = document.getElementById(DOMstrings.divCal);
            child.parentNode.removeChild(child);
        }
    }

    function DateParse(date) {
        let parsedDate, newDate;
        const currentDate = date;
        if (currentDate != null) {
            splitedDate = currentDate.split('-');
            newDate = {year: splitedDate[0], month: splitedDate[1], day: splitedDate[2]};
        }
        return newDate;
    }

    return {open: open, switchMonth: switchMonth, createCal: createCal, dayClick: dayClick, close: close};
})();
function fadeOutEffect(selector, callback) {
    var fadeTarget = document.querySelector(selector);
    if (fadeTarget != null) {
        var fadeEffect = setInterval(function () {
            if (!fadeTarget.style.opacity) {
                fadeTarget.style.opacity = 1;
            }
            if (fadeTarget.style.opacity > 0) {
                fadeTarget.style.opacity -= 0.1;
            } else {
                clearInterval(fadeEffect);
                callback();
            }
        }, 20);
    }
}