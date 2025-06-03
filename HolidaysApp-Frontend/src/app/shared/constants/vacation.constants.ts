import Swal from 'sweetalert2';

const vacationTypeOptions = {
  Vacaciones: 'Vacaciones',
  Personales: 'Días personales',
  Enfermedad: 'Baja por enfermedad',
  Otro: 'Otro'
};


const setUTCDate = (date: Date): Date =>
  new Date(Date.UTC(
    date.getFullYear(),
    date.getMonth(),
    date.getDate()
  ));

// Convierte la fecha a un formato de entrada de fecha HTML sin errores.
const toDateInputValue = (date: Date): string => {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, '0');
  const d = String(date.getDate()).padStart(2, '0');
  return `${y}-${m}-${d}`;
};

const parseInputDate = (value: string): Date => {
  const [y, m, d] = value.split('-').map(Number);
  return new Date(y, m - 1, d);
}

const showErrorAlert = (text: string): void => {
  Swal.fire({
    icon: 'error',
    title: 'Error',
    text,
    confirmButtonText: 'Vale',
    confirmButtonColor: '#153A7B',
  });
};

const getRandomColor = () => '#' + Math.floor(Math.random() * 0x1000000).toString(16).padStart(6, '0');


export { vacationTypeOptions, setUTCDate, toDateInputValue, parseInputDate, showErrorAlert, getRandomColor };