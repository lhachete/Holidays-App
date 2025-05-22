const vacationTypeOptions = {
    Vacation: 'Vacation',
    PTO:      'Paid Time Off',
    Sick:     'Sick Leave',
    Other:    'Other'
  };

    const setUTCDate = (date: Date): Date =>
    new Date(Date.UTC(
      date.getFullYear(),
      date.getMonth(),
      date.getDate()
    ));

export {vacationTypeOptions, setUTCDate};