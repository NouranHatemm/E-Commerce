import { PostProductFaqComponent } from './components/post-product-faq/post-product-faq.component';
import { PostproductComponent } from './components/postproduct/postproduct.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { OrdersComponent } from './components/orders/orders.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';

const routes: Routes = 
[ { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {path: 'dashboard', component: DashboardComponent},
  {path: 'category', component: PostCategoryComponent},
  {path: 'product', component: PostproductComponent},
  {path: 'orders', component: OrdersComponent},
  {path: 'product/:productId', component: UpdateProductComponent},
  {path: 'faq/:productId', component: PostProductFaqComponent}



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
