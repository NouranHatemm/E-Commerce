// import { UserStorageService } from './../storage/user-storage.service';
// import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
// import { environment } from 'src/environments/environments';

// const BASIC_URL = environment["BASIC_URL"]

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {

//   constructor(private http: HttpClient) { }

//   register(signupDTO: any): Observable<any> {
//     return this.http.post(BASIC_URL + "sign-up", signupDTO)
//   }

//   login(username: string, password: string): any {
//     const headers = new HttpHeaders().set('Content-Type', 'application/json');
//     const body = (username , password);
//     return this.http.post(BASIC_URL + "authenticate", body, { headers, observe: 'response' }).pipe(
//       map((res) => {
//         const token = res.headers.get('authorization').substring(7);
//         const user = res.body;
//         if (token && user) {
//           this.userStorageService.saveToken(token);
//           this.userStorageService.saveUser(user);
//           return true;
//         }
//         return false;
//       })
//     )

//   }
// }

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserStorageService } from './../storage/user-storage.service'; // Assuming this is the correct path
import { environment } from 'src/environments/environments';

const BASIC_URL = environment.BASIC_URL;

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService
  ) { }

  register(signupRequest: any): Observable<any> {
    return this.http.post(BASIC_URL + 'sign-up', signupRequest);
  }

  login(username: string, password: string): any {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username, password };
    console.log('url ', BASIC_URL + 'authenticate')
    return this.http
      .post(BASIC_URL + 'authenticate', JSON.stringify({ username: username, password: password }),
       { headers, observe: 'response' })
      .pipe(
        map((res) => {
          console.log('respose from back : ', res);
          // const token = res.headers.get('Authorization')?.substring(7);
          const token = res.body['token'];
          console.log('token : ', token); 
          const user = res.body;
          console.log('user : ', user);
          if (token && user) {
            this.userStorageService.saveToken(token);
            this.userStorageService.saveUser(user);
            return true;
          }
          return false;
        })
      );
  }

  getOrderByTrackingId(trackingId: number): Observable<any> {
    return this.http.get(BASIC_URL + `order/${trackingId}`);
  }

}

