import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environments';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';


const BASIC_URL = environment.BASIC_URL;

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor( private http: HttpClient , private UserStorageService:UserStorageService) { }

  
  getAllProducts(): Observable<any>{
    return this.http.get(BASIC_URL + 'api/customer/products',{
      headers: this.createAuthorizationHeader(),
    })
  }

  getAllProductsByName(name:any): Observable<any>{
    return this.http.get(BASIC_URL + `api/customer/search/${name}`,{
      headers: this.createAuthorizationHeader(),
    })
  }


  addToCart(productId:any): Observable<any>{
    const cartDto = {
      productId: productId,
      userId: this.UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL + `api/customer/cart`, cartDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  increaseProductQuantity(productId:any): Observable<any>{
    const cartDto = {
      productId: productId,
      userId: this.UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL +'api/customer/addition', cartDto, {
      headers: this.createAuthorizationHeader(),
    })
  }


  decreaseProductQuantity(productId:any): Observable<any>{
    const cartDto = {
      productId: productId,
      userId: this.UserStorageService.getUserId()
    }
    return this.http.post(BASIC_URL + 'api/customer/deduction', cartDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  placeOrder(orderDto: any): Observable<any>{
    const userId = this.UserStorageService.getUserId()
    return this.http.post(BASIC_URL + `api/customer/placeOrder`, orderDto, {
      headers: this.createAuthorizationHeader(),
    })
  }

  getOrdersByUserId(): Observable<any>{
    const userId = this.UserStorageService.getUserId();
    return this.http.post(BASIC_URL + `api/customer/myOrders/${userId}`, {
      headers: this.createAuthorizationHeader(),
    })
  }


  getCartByUserId(): Observable<any>{
    const userId = this.UserStorageService.getUserId()
    
    return this.http.get(BASIC_URL + 'api/customer/cart', {
      headers: this.createAuthorizationHeader(),
    })
  }

  getProductDetailsById(productId: number) : Observable<any>{
    return this.http.get(BASIC_URL + `api/customer/product/${productId}`,{
      headers: this.createAuthorizationHeader(),
    })
  }

  private createAuthorizationHeader(): HttpHeaders{
    return new HttpHeaders().set(
      'Authorization', 'Bearer' + this.UserStorageService.getToken()
    )
  }
}