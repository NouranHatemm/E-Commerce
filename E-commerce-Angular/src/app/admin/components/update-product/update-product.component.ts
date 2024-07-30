import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from './../../service/admin.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent {

  productId = this.activatedRoute.snapshot.params['productId'];

  productform!: FormGroup;
  listOfCategories: any = [];
  selectedFile: File | null;
  imagePreview: string | ArrayBuffer | null;

  existingImage: string | null = null;
  imgChanged = false; 


  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
  ) { }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChanged = true;
    this.existingImage = null;
  }

  previewImage() {
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
    this.getProductById();
  }


  getAllCategories() {
    this.adminService.getAllCategories().subscribe(res => {
      this.listOfCategories = res;
    })
  }

  getProductById() {
    this.adminService.getProductById(this.productId).subscribe(res => {
      console.log("product", this.productform);
       this.productform.patchValue(res);
      this.existingImage = 'data:image/jpeg;base64,' +res.byteImg;
    })
  }

  updateProduct(): void {
    if (this.productform.valid) {
      const formData: FormData = new FormData();

      if(this.imgChanged && this.selectedFile){
         formData.append('img', this.selectedFile); 
      }
    
      formData.append('categoryId', this.productform.get('categoryId').value);
      formData.append('name', this.productform.get('name').value);
      formData.append('description', this.productform.get('description').value);
      formData.append('price', this.productform.get('price').value);

      this.adminService.updateProduct(this.productId, formData).subscribe((res) => {
        if (res.id !== null) {
          this.snackBar.open('Product Updated Successfully', 'close', {
            duration: 5000
          });
          this.router.navigateByUrl('/admin/dashboard');
        } else {
          this.snackBar.open(res.message, 'ERROR', {
            duration: 5000,

          });
        }
      })

    } else {
      for (const i in this.productform.controls) {
        this.productform.controls[i].markAsDirty();
        this.productform.controls[i].updateValueAndValidity();
      }

    }
  }
}