import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { DataSource } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent {

  displayedColumns: string[] = ['trackingId','amount','orderDescription','date'];
  orders:any;

  constructor(private customerService: CustomerService){}

  ngOnInit(){
    this.getMyOrders();
  }

  getMyOrders(){
    this.customerService.getOrdersByUserId().subscribe(res => {
      console.log("response", res)
      this.orders = res;
      
    })
  }

}
  

