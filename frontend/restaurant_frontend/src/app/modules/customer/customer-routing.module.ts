import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './customer-components/dashboard/dashboard.component';
import { ViewProductsCustomerComponent } from './customer-components/view-products-customer/view-products-customer.component';

const routes: Routes = [
  {path: "dashboard", component: DashboardComponent},
  {path: "products/:categoryId", component: ViewProductsCustomerComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
