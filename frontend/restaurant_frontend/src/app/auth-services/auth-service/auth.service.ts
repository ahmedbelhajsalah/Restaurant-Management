import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASIC_URL = ["http://localhost:8080/"];

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private Http: HttpClient) {}

  signUp(signupRequest: any):Observable<any>{
    return this.Http.post<[]>(BASIC_URL + "api/auth/signup", signupRequest);
  }

  login(loginRequest: any){
    return this.Http.post<User>(BASIC_URL + "api/auth/login", loginRequest);
  }

  

}

export interface User{
    name:string,
    userId: string,
    userRole: string,
    jwt: string
}
