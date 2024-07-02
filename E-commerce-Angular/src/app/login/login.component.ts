import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth-service/auth.service';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
loginForm!: FormGroup;
hidePassword= true;

constructor(private fb: FormBuilder,
  private snackBar: MatSnackBar,
  private authService: AuthService,
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

    // this.authService.login(username, password).subscribe(
    //   (res) =>{
    //     if (UserStorageService.isAdminLoggedIn()){
    //       this.router.navigateByUrl('admin/dashboard');
    //     } else if(UserStorageService.isCustomerLoggedIn){
    //       this.router.navigateByUrl('customer/dashboard');
    //     }
    //   },
    //   (error)=>{
    //     this.snackBar.open('Bad Credentials', 'ERROR,', { duration:5000} )
    //   }
    // )
  }
}
