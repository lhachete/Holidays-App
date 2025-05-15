import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  
})
export class HomeComponent {

    constructor(private authService: AuthService) { }
    
    get user(): any {
      return this.authService.user;
    }
    
    get isAuthenticated(): boolean {
      return this.authService.isLoggedIn;
    }

}
