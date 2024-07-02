import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-product-details',
  templateUrl: './view-product-details.component.html',
  styleUrls: ['./view-product-details.component.css']
})
export class ViewProductDetailsComponent {

  productId: number = this.activatedRoute.snapshot.params["productId"];

  product: any;
  FAQs: any[] = [];
  reviews: any[] = [];

  constructor(private snackBar: MatSnackBar,
    private customerService: CustomerService,
    private activatedRoute: ActivatedRoute
  ) {}
 

  ngOnInit(){
    this.getProductDetailsById(); 
  }


  getProductDetailsById(){
    this.customerService.getProductDetailsById(this.productId).subscribe(res => {
      this.product = res.productDto;
      this.product.proccessedImg = 'data:image/png;base64, ' + res.productDto.returnedImg;

      this.FAQs = res.faqDtoList;

      res.reviewDtoList.forEach(element => {
        element.proccessedImg = 'data:image/png;base64, ' + element.returnedImg;
        this.reviews.push(element);
        
      });
    })
  }
}