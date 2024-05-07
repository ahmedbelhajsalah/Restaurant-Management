import { Injectable } from '@angular/core';

const TOKEN_KEY = 'token';
const USER_KEY = 'user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token: string): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.setItem(TOKEN_KEY, token);
    }
  }

  static saveUser(user: {id: string, role: string}): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(USER_KEY);
      localStorage.setItem(USER_KEY, JSON.stringify(user));
    }
  }

  static getToken(): string {
    if (typeof localStorage !== 'undefined') {
      return localStorage.getItem(TOKEN_KEY) || '';
    }
    return '';
  }

  static getUser(): any {
    if (typeof localStorage !== 'undefined') {
      const user = localStorage.getItem(USER_KEY);
      return user ? JSON.parse(user) : '';
    }
    return '';
  }

  static getUserRole(): string {
    const user = this.getUser();
    return user ? user.role : '';
  }

  static isAdminLoggedIn(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

  static isCustomerLoggedIn(): boolean {
    return this.getUserRole() === 'CUSTOMER';
  }

  static logout(){
    if(typeof localStorage !== 'undefined'){
      window.localStorage.removeItem(USER_KEY);
      window.localStorage.removeItem(TOKEN_KEY);
    }
  }
}
