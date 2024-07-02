import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
Orders: any;
constructor(private adminService: AdminService,
  private snackBar: MatSnackBar
){}

ngOnInit(){
  this.getPlacedOrder();
}
getPlacedOrder(){
  this.adminService.getPlacedOrders().subscribe(res =>{
    this.Orders=res;
  })
}
}
