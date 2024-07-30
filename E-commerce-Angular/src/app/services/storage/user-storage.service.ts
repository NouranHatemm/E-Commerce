import { Injectable } from '@angular/core';

const TOKEN = 'ecom-token';
const USER = 'ecom-user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {
  // static getUserId() {
  //   throw new Error('Method not implemented.');
  // }

  constructor() { }

  public saveToken(token: string): void {
    localStorage.removeItem(TOKEN);
    localStorage.setItem(TOKEN, token);
  }


  public saveUser(user): void {
    localStorage.removeItem(USER);
    localStorage.setItem(USER, JSON.stringify(user));
  }

   getToken(): string {
    return localStorage.getItem(TOKEN);
  }

   getUser(): any {
    console.log("localStorage",localStorage);
    return JSON.parse(localStorage.getItem(USER));
  }

   getUserId(): any {
    const user = this.getUser()["userID"];
    console.log("login user ",user);
    if (user == null) {
     return null;
    }
    return user;
  }
   getUserRole(): string {
    const user = this.getUser();
    if (user == null) {
      return '';
    }
    return user.role;
  }

   isAdminLoggedIn(): boolean {
    console.log('isAdminLoggedIn');
    console.log('LS ',localStorage.getItem(TOKEN))
    if (this.getToken() === null) {
      console.log('false = null');
      return false;
    }
    const role: string = this.getUserRole();
    return role == 'ADMIN';
  }

  
   isCustomerLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false;
    }
    const role: string = this.getUserRole();
    return role == 'CUSTOMER';
  }

   signOut(): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }


}
