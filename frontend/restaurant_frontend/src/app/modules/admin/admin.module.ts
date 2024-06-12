import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './admin-components/dashboard/dashboard.component';
import { AddCategoryComponent } from './admin-components/add-category/add-category.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';

import {MatToolbarModule} from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import { PostProductComponent } from './admin-components/post-product/post-product.component';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatCardModule} from '@angular/material/card';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDividerModule} from '@angular/material/divider';
import { ViewProductsComponent } from './admin-components/view-products/view-products.component';
import { UpdateProductComponent } from './admin-components/update-product/update-product.component';
import { AboutSectionComponent } from '../common-components/about-section/about-section.component';
import { PenguinGreetingsComponent } from '../common-components/penguin-greetings/penguin-greetings.component';
import { UpdateCategoryComponent } from './admin-components/update-category/update-category.component';
import { RatingStarsComponent } from '../common-components/rating-stars/rating-stars.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    DashboardComponent,
    AddCategoryComponent,
    PostProductComponent,
    ViewProductsComponent,
    UpdateProductComponent,
    AboutSectionComponent,
    PenguinGreetingsComponent,
    UpdateCategoryComponent,
    RatingStarsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatFormFieldModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    MatCardModule,
    MatProgressBarModule,
    MatDividerModule,
    FontAwesomeModule
  ],
  exports: [
    AboutSectionComponent,
    PenguinGreetingsComponent,
    RatingStarsComponent,
    FontAwesomeModule
  ],
})
export class AdminModule { }
