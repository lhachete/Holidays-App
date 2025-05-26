import { Component } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faUser, faSignOutAlt, faAddressCard } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, RouterLink, FontAwesomeModule],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {
  faUser = faUser;
  faSignOutAlt = faSignOutAlt;
  faAddressCard = faAddressCard;

  constructor(private authService: AuthService) { }
  
  get user(): any {
    return this.authService.user;
  }

  get isAuthenticated(): boolean {
    return this.authService.isLoggedIn;
  }

  logout(): void {
    this.authService.logout();
  }

}
