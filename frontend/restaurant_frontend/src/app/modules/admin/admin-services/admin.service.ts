import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage-service/storage.service';

const BASIC_URL= ["http://localhost:8080/"];

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  

  constructor(private http: HttpClient) { }

  postCategory(categoryDto: any): Observable<any>{
    return this.http.post<any>(BASIC_URL +"api/admin/category", categoryDto,{
      headers: this.creatAuthorizationHeader()
    });
  }
  
  creatAuthorizationHeader():HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      "Authorization", "Bearer "+ StorageService.getToken()
    )
  }

  getAllCategories(): Observable<any>{
    return this.http.get<any>(BASIC_URL +"api/admin/categories",{
      headers: this.creatAuthorizationHeader()
    });
  }

  getAllCategoriesByTitle(title: string): Observable<any>{
    return this.http.get<any>(BASIC_URL +`api/admin/categories/${title}`,{
      headers: this.creatAuthorizationHeader()
    });
  }

  postProductById(productId: number, categoryDto: any): Observable<any>{
    return this.http.post<any>(BASIC_URL +`api/admin/product/${productId}`, categoryDto,{
      headers: this.creatAuthorizationHeader()
    });
  }

  getAllProductsByCategory(categoryId: number): Observable<any>{
    return this.http.get<any>(BASIC_URL +`api/admin/products/${categoryId}`,{
      headers: this.creatAuthorizationHeader()
    });
  }

  deleteProduct(productTd: number): Observable<any> {
    console.log('product Id: ', productTd);
    return this.http.delete<any>(BASIC_URL + `api/admin/products/${productTd}`,{
      headers: this.creatAuthorizationHeader()
    });
  }

  getProductById(productId: number): Observable<any>{
    return this.http.get<any>(BASIC_URL +`api/admin/product/${productId}`,{
      headers: this.creatAuthorizationHeader()
    });
  }

  updateProduct(productId: number, productDto: any): Observable<any>{
    return this.http.put<any>(BASIC_URL + `api/admin/updatedProduct/${productId}`, productDto,{
      headers: this.creatAuthorizationHeader()
    })
  }
}
