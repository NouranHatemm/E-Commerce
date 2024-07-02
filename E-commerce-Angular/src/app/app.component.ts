import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { UserStorageService } from './services/storage/user-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ECommerceWeb';
  constructor(private UserStorageService:UserStorageService,private router: Router){}

  isCustomerLoggedIn : boolean = this.UserStorageService.isCustomerLoggedIn();
  isAdminLoggedIn : boolean = this.UserStorageService.isAdminLoggedIn();


  ngOnInit(): void {
    this.router.events.subscribe(event => {
      this.isCustomerLoggedIn = this.UserStorageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = this.UserStorageService.isAdminLoggedIn();
    })
  }

  logOut(){
    this.UserStorageService.signOut();
    this.router.navigateByUrl('login');
  }
}
