import { Component } from '@angular/core';
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-home',
  imports: [NavbarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  username: string = '';
  
  constructor(private userService: UserService) {
    // Aquí puedes inicializar el nombre de usuario o cualquier otra lógica que necesites
    this.username = userService.username; // Cambia esto por la lógica que necesites para obtener el nombre de usuario
  }
}
