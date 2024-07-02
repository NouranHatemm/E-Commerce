import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth-service/auth.service';

@Component({
  selector: 'app-track-order',
  templateUrl: './track-order.component.html',
  styleUrls: ['./track-order.component.css']
})
export class TrackOrderComponent {

  searchOrderForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private authService: AuthService
  ){}

  ngOnInit(){
    this.searchOrderForm = this.fb.group({
      trackingId: [null, [Validators.required]]
    })
  }

  submitForm(){
    this.authService.getOrderByTrackingId(this.searchOrderForm.get('trackingId').value).subscribe(res =>{
      console.log(res);
    })
  }

}
