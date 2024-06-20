import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth-services/storage-service/storage.service';
import { Observable } from 'rxjs';

const BASIC_URL= ["http://localhost:8080/"];

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  creatAuthorizationHeader():HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      "Authorization", "Bearer "+ StorageService.getToken()
    )
  }

  getAllCategories(): Observable<any>{
    return this.http.get<any>(BASIC_URL +"api/customer/categories",{
      headers: this.creatAuthorizationHeader()
    });
  }

  getAllProductsByCategory(categoryId: number): Observable<any>{
    return this.http.get<any>(BASIC_URL +`api/customer/products/${categoryId}`,{
      headers: this.creatAuthorizationHeader()
    });
  }

  postRating(rating: Rating): Observable<Rating>{

    return this.http.post<Rating>(BASIC_URL +`api/customer/product/rate`, rating,{
      headers: this.creatAuthorizationHeader()
    });
  }

  getAverageRating(productId: number): Observable<number>{
    return this.http.get<number>(BASIC_URL +`api/customer/product/averageRating/${productId}`,{
      headers: this.creatAuthorizationHeader()
    });
  }
}
export interface Rating {
  userId: number;
  productId: number;
  rating: number;
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: string;
  returnedImg: string;
  averageRating?: number;
}
