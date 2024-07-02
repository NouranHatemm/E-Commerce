import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from './../../service/admin.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-postproduct',
  templateUrl: './postproduct.component.html',
  styleUrls: ['./postproduct.component.css']
})
export class PostproductComponent {
  productform: FormGroup;
  listOfCategories: any=[];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;


  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService
  ){}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader;
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  ngOnInit(): void {
    this.productform = this.fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      categoryId: [null, [Validators.required]],
      price: [null, [Validators.required]],

    });
    this.getAllCategories();
  }
  

  getAllCategories(){
    this.adminService.getAllCategories().subscribe(res => {
      this.listOfCategories = res;
    })
  }

  addProduct(): void {
    if(this.productform.valid){
      const formData: FormData = new FormData();
      formData.append('img', this.selectedFile);
      formData.append('categoryId', this.productform.get('categoryId').value);
      formData.append('name', this.productform.get('name').value);
      formData.append('description', this.productform.get('description').value);
      formData.append('price', this.productform.get('price').value);

      this.adminService.addProduct(this.productform.value).subscribe((res) =>{
        if(res.id !== null){
          this.snackBar.open('Product Posted Successfully', 'close',{
            duration: 5000
          });
          this.router.navigateByUrl('/admin/dashboard');
        }else{
          this.snackBar.open(res.message, 'ERROR',{
            duration: 5000,
            
          });
        }
      })
    
    }else{
      for(const i in this.productform.controls){
        this.productform.controls[i].markAsDirty();
        this.productform.controls[i].updateValueAndValidity();
      }

    }
  }
}
