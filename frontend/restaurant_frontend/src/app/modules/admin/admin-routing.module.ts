import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './admin-components/dashboard/dashboard.component';
import { AddCategoryComponent } from './admin-components/add-category/add-category.component';
import { PostProductComponent } from './admin-components/post-product/post-product.component';
import { ViewProductsComponent } from './admin-components/view-products/view-products.component';
import { UpdateProductComponent } from './admin-components/update-product/update-product.component';
import { UpdateCategoryComponent } from './admin-components/update-category/update-category.component';

const routes: Routes = [
  {path: "dashboard", component: DashboardComponent},
  {path: "category", component: AddCategoryComponent},
  {path: "product/:categoryId", component: PostProductComponent},
  {path: "products/:categoryId", component: ViewProductsComponent},
  {path: "products/updateProduct/:productId", component: UpdateProductComponent},
  {path: "updateCategory/:categoryId", component: UpdateCategoryComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
