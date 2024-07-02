import { CustomerService } from './../../services/customer.service';
import { Component } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  products: any[] = [];
  searchProductForm!: FormGroup;
  
    constructor(private customerService: CustomerService, 
      private fb:FormBuilder,
    private snackBar: MatSnackBar, ){}
  
    ngOnInit(){
      this.getAllProducts();
      this.searchProductForm = this.fb.group({
       title: [null, [Validators.required]]
      })
  
    }
  
    getAllProducts(){
      this.products = [];
      this.customerService.getAllProducts().subscribe(res =>{
        res.forEach(element => {
          element.proccessedImg = 'data:image/jpeg;base64' + element.byteImg;
          this.products.push(element);
        });
        console.log(this.products)
      })
    } 
    
    submitForm(){
      this.products = [];
      const title = this.searchProductForm.get('title')!.value;
      this.customerService.getAllProductsByName(title).subscribe(res =>{
        res.forEach(element => {
          element.proccessedImg = 'data:image/jpeg;base64' + element.byteImg;
          this.products.push(element);
        });
        console.log(this.products)
      })
    } 
  
    // deleteProduct(productId:any){
    //   this.customerService.deleteProduct(productId).subscribe(res =>{
    //     if(res.body == null){
    //       this.snackBar.open('Product Daleted Successfully!', 'Close', {
    //         duration: 5000,
    //       });
    //     }else{
    //       this.snackBar.open(res.message, 'close',{
    //         duration: 5000,
    //         panelClass: 'error-snackbar'
    //       });
    //     }
    //   })
    // }
  

    addToCart(id:any){
      this.customerService.addToCart(id).subscribe(res =>{
        this.snackBar.open("Produc added to cart successfully", "close", {duration: 5000})
      })
    }

}
