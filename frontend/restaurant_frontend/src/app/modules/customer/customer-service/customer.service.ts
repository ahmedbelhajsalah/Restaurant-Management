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
}
