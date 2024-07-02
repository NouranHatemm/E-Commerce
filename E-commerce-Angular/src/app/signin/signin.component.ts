import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../services/auth-service/auth.service';
import { Router } from '@angular/router';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent {
  loginForm!: FormGroup;
  hidePassword= true;
  
  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private UserStorageService:UserStorageService,
    private router: Router) { 
  
    }
  
    ngOnInit():void {
      this.loginForm = this.fb.group({
        name: [null, [Validators.required]],
        email: [null, [Validators.required, Validators.email]],
        password: [null, [Validators.required]],
        confirmPassword: [null, [Validators.required]],
      })
    }
    togglePasswordVisibility(){
  
      this.hidePassword = !this.hidePassword;
    }
  
    onSubmit(): void {
      const password = this.loginForm.get('password')!.value;
      const username = this.loginForm.get('email')!.value;
  
      console.log(username,password);
      this.authService.login(username, password).subscribe(
        (res) =>{
          console.log('res ',res);
          if (this.UserStorageService.isAdminLoggedIn()){
            this.router.navigateByUrl('admin/dashboard');
          } else if(this.UserStorageService.isCustomerLoggedIn()){
            this.router.navigateByUrl('customer/dashboard');
          } 
        },
        (error)=>{
          console.log(error);
          this.snackBar.open('Bad Credentials', 'ERROR', { duration:5000} )
        
      }
      )
      
    }
   
}
