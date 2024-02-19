import dayjs from "dayjs";

export const generateDate = (month = dayjs().month(), year = dayjs().year()) => {
    const currentDate = dayjs(); // Get the current date for comparison
    const firstDateOfMonth = dayjs().year(year).month(month).startOf("month");
    const lastDateOfMonth = dayjs().year(year).month(month).endOf("month");

    const arrayOfDate = [];

    // Create prefix dates (before the first day of the month)
    for (let i = 0; i < firstDateOfMonth.day(); i++) {
        const date = firstDateOfMonth.subtract(firstDateOfMonth.day() - i, 'day');
        arrayOfDate.push({
            currentMonth: false,
            date,
            isPast: date.isBefore(currentDate, 'day'), // Check if the date is before the current date
        });
    }

    // Generate dates for the current month
    for (let i = 0; i <= lastDateOfMonth.diff(firstDateOfMonth, 'day'); i++) {
        const date = firstDateOfMonth.add(i, 'days');
        arrayOfDate.push({
            currentMonth: true,
            date,
            today: date.isSame(currentDate, 'day'), // Check if the date is today
            isPast: date.isBefore(currentDate, 'day'), // Check if the date is before the current date
        });
    }

    // Append remaining dates to make a full grid, if necessary
    const remaining = 42 - arrayOfDate.length;
    for (let i = 1; i <= remaining; i++) {
        const date = lastDateOfMonth.add(i, 'days');
        arrayOfDate.push({
            currentMonth: false,
            date,
            isPast: date.isBefore(currentDate, 'day'), // This will always be false as these are future dates
        });
    }

    return arrayOfDate;
};

export const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
];