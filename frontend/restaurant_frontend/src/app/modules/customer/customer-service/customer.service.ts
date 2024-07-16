  import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { StorageService } from '../../../auth-services/storage-service/storage.service';
  import { Observable, of } from 'rxjs';

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

    getProductById(productId: number): Observable<any>{
      return this.http.get<any>(BASIC_URL +`api/customer/product/${productId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    postPaypal(priceToPay: Number): Observable<any>{
      return this.http.post<any>(BASIC_URL +`api/customer/payment/create/${priceToPay}`, { responseType: 'text' },{
        headers: this.creatAuthorizationHeader()
      });
    }
    postComment(productId: number,userId: number, comment: string): Observable<any>{
      return this.http.post<any>(BASIC_URL +`api/customer/product/createComment`, {
        id: 0,
        product_id: productId,
        user_id: userId,
        content: comment
      },{
        headers: this.creatAuthorizationHeader()
      });
    }
    getCommentsByProductId(product_id: number): Observable<Comment[]>{
      return this.http.get<any>(BASIC_URL +`api/customer/product/getAllCommentsByProductId/${product_id}`,{
        headers: this.creatAuthorizationHeader()
      });
    }
    postReply(commentId: number, userId: number, content: string): Observable<any> {
      return this.http.post<any>(BASIC_URL + `api/customer/comment/reply`, {
        comment_id: commentId,
        user_id: userId,
        content: content
      }, {
        headers: this.creatAuthorizationHeader()
      });
    }
    commentLike(comment_id: number, user_id: number): Observable<any>{
      return this.http.post<any>(BASIC_URL + `api/customer/comment/like`, {
        comment_id: comment_id,
        user_id: user_id,
      }, {
        headers: this.creatAuthorizationHeader()
      });
    }
    replyLike(reply_id: number, user_id: number): Observable<any>{
      return this.http.post<any>(BASIC_URL + `api/customer/reply/like`, {
        reply_id: reply_id,
        user_id: user_id,
      }, {
        headers: this.creatAuthorizationHeader()
      });
    }

    getAllReplyByCommentId(comment_id: number): Observable<any>{
      let params = new HttpParams().set('comment_id', comment_id.toString());
      return this.http.get<any>(BASIC_URL +`api/customer/reply/getAllRepliesPerComment`,{
        params,
        headers: this.creatAuthorizationHeader()
      });
    }

    deleteComment(comment_id: number): Observable<Comment> {
      return this.http.delete<Comment>(BASIC_URL + `api/customer/deleteComments/${comment_id}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    deleteReply(reply_id: number): Observable<Comment> {
      return this.http.delete<Comment>(BASIC_URL + `api/customer/deleteReply/${reply_id}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    countLikesByComment(commentId: number): Observable<number>{
      return this.http.get<number>(BASIC_URL +`api/customer/countCommentLike/${commentId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    countLikesByReply(replyId: number): Observable<number>{
      return this.http.get<number>(BASIC_URL +`api/customer/countReplyLike/${replyId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    getUserNameById(userId: number): Observable<{ userName: string }> {
      return this.http.get<{ userName: string }>(BASIC_URL +`api/customer/${userId}/name`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    isCommentLiked(commentId: number, userId: number): Observable<boolean>{
      return this.http.get<boolean>(BASIC_URL +`api/customer/isCommentLiked/${commentId}/${userId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    unlikeComment(commentId: number, userId: number): Observable<any>{
      return this.http.delete<any>(BASIC_URL + `api/customer/unlikeComment/${commentId}/${userId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    isReplyLiked(replyId: number, userId: number): Observable<boolean>{
      return this.http.get<boolean>(BASIC_URL +`api/customer/isReplyLiked/${replyId}/${userId}`,{
        headers: this.creatAuthorizationHeader()
      });
    }

    unlikeReply(replyId: number, userId: number): Observable<any>{
      return this.http.delete<any>(BASIC_URL + `api/customer/unlikeReply/${replyId}/${userId}`,{
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

  export interface Reply {
    id: number;
    content: string;
    user_id: number;
    comment_id: number;
    likes: number;
    userName?: string;
  }
  
  export interface Comment {
    id: number;
    content: string;
    user_id: number;
    product_id: number;
    likes: number;
    replies: Reply[];
    userName?: string;
  }
  
