import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth-services/storage-service/storage.service';

const BASIC_URL= ["http://localhost:8080/"]

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
}
