import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { DashboardComponent } from './customer-components/dashboard/dashboard.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatCardModule} from '@angular/material/card';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDividerModule} from '@angular/material/divider';
import { ViewProductsCustomerComponent } from './customer-components/view-products-customer/view-products-customer.component';
import { AdminModule } from '../admin/admin.module';
import { RatingDialogComponent } from './customer-components/rating-dialog/rating-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import { DisplayStarsComponent } from '../common-components/display-stars/display-stars.component';
import { ShowDetailsComponent } from './customer-components/show-details/show-details.component';
import { KeenSliderComponent } from '../common-components/keen-slider/keen-slider.component';
import { BookDialogComponent } from '../common-components/book-dialog/book-dialog.component';

@NgModule({
  declarations: [
    DashboardComponent,
    ViewProductsCustomerComponent,
    RatingDialogComponent,
    ShowDetailsComponent,
    KeenSliderComponent,
    BookDialogComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
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
    AdminModule,
    MatDialogModule,
  ]
})
export class CustomerModule { }
