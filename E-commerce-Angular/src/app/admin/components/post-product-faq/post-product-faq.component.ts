import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-post-product-faq',
  templateUrl: './post-product-faq.component.html',
  styleUrls: ['./post-product-faq.component.css']
})
export class PostProductFaqComponent {

  productId: number = this.activatedRoute.snapshot.params["productId"];
  FAQForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private ruter: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute
  ){}

  ngOnInit(){
    this.FAQForm = this.fb.group({
      question: [null,[Validators.required]],
      answer: [null,[Validators.required]],
    })
  }

  postFAQ(){
    this.adminService.postFAQ(this.productId, this.FAQForm.value).subscribe(res =>{
      if(res.id != null){
        this.snackBar.open('FAQ Posted Succesfully!', 'Close',{
          duration:5000
        } );

      }else{
        this.snackBar.open("Something Went Wrong", 'close', {
          duration: 5000,
          panelClass: 'error-snackbar'
        });
      }
    } )
  }


}
