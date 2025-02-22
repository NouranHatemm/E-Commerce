import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
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

  constructor(private adminService: AdminService, 
    private fb:FormBuilder,
  private snackBar: MatSnackBar, ){}

  ngOnInit(){
    console.log("dashboard");
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
     title: [null, [Validators.required]]
    })

  }

  getAllProducts(){
    this.products = [];
    this.adminService.getAllProducts().subscribe(res =>{
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
    this.adminService.getAllProductsByName(title).subscribe(res =>{
      res.forEach(element => {
        element.proccessedImg = 'data:image/jpeg;base64' + element.byteImg;
        this.products.push(element);
      });
      console.log(this.products)
    })
  } 

  deleteProduct(productId:any){
    this.adminService.deleteProduct(productId).subscribe(res =>{
      console.log('res ',res);
      if(res == null){
        this.snackBar.open('Product Daleted Successfully!', 'Close', {
          duration: 5000,
        });
        this.getAllProducts();
      }else{
        this.snackBar.open(res.message, 'close',{
          duration: 5000,
          panelClass: 'error-snackbar'
        });
      }
    })
  }

}
