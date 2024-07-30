

import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from './../../services/customer.service';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder } from '@angular/forms';
import { PlaceOrderComponent } from '../place-order/place-order.component';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItem: any[] = [];
  order: any;

  constructor(
    private customerService: CustomerService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    public fb: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.getCart();
  }

  getCart(): void {
    this.cartItem = [];
    this.customerService.getCartByUserId().subscribe(
      res => {
        console.log("result" , res);
        this.order = res;
        res.cartItems.forEach( element => {
          console.log("element", element);
          element.proccessedImg = 'data:image/jpeg:base64,' + element.returnedImg;
          console.log("image", element.proccessedImg);
          this.cartItem.push(element);
        });
      });
  }

  increaseQuantity(productId: any){
    this.customerService.increaseProductQuantity(productId).subscribe(res =>{
      this.snackBar.open('Product Quantity increased', 'Close', {duration:5000} );
      this.getCart();
    })
  }

  decreaseQuantity(productId: any){
    this.customerService.increaseProductQuantity(productId).subscribe(res =>{
      this.snackBar.open('Product Quantity decreased', 'Close', {duration:5000} );
      this.getCart();
    })
  }

  placeOrder(){
    this.dialog.open(PlaceOrderComponent);
  }
}


