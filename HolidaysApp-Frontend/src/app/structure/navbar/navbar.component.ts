import { Component } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, RouterLink],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {

  constructor(private authService: AuthService) { }
  
  get isAuthenticated(): boolean {
    return this.authService.isLoggedIn;
  }

  logout(): void {
    this.authService.logout();
  }

}
