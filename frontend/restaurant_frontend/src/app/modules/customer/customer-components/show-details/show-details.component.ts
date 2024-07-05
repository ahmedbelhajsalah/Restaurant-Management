import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../customer-service/customer.service';
import { MatDialog } from '@angular/material/dialog';
import { BookDialogComponent } from '../../../common-components/book-dialog/book-dialog.component';
import { StorageService } from '../../../../auth-services/storage-service/storage.service';

@Component({
  selector: 'app-show-details',
  templateUrl: './show-details.component.html',
  styleUrl: './show-details.component.css'
})
export class ShowDetailsComponent implements OnInit {
likeReply(arg0: any) {
throw new Error('Method not implemented.');
}
replyingToCommentId: any;
replyContent: any;
likeComment(arg0: any) {
throw new Error('Method not implemented.');
}


  readonly dialog = inject(MatDialog);
  price: number= 0;
  userId: number = 0;
  newComment: string = '';

  constructor(private activatedRouter: ActivatedRoute, private customerService: CustomerService){}
  ngOnInit(): void {
    this.getProdutById(this.productId);
    this.fetchComments();
    this.userId = StorageService.getUserId();
  }

  productId: any = this.activatedRouter.snapshot.params['productId'];
  productName: string= '';
  productAdditionalImages: string[] = [];
  ImagesCount : number[] = [];
  productAdditionalDescription: string ='';
  comments: any[] = [];

  getProdutById(productId: number) {
    this.customerService.getProductById(productId).subscribe(
      data => {
        this.productName = data.name;
        this.productAdditionalImages = data.returnedAdditionalImages.map((image: any) => 'data:image/jpeg;base64,' + image);
        this.ImagesCount = this.productAdditionalImages.map((_, index) => index);
        this.productAdditionalDescription = data.detailedDescription;
        this.price = data.price
      }
    );
  }
  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(BookDialogComponent, {
      width: '400px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: this.price,
      panelClass: "book-dialog-class"
    });
  }
  postComment() {
    this.customerService.postComment(this.productId, this.userId, this.newComment).subscribe((data)=>
      console.log('passed by here', data)
    )
  }

  fetchComments() {
    this.customerService.getCommentsByProductId(this.productId).subscribe(comments => {
      this.comments = comments;
    });
  }
  postReply(commentId: number) {
    if (this.replyContent.trim()) {
      this.customerService.postReply(commentId, this.userId, this.replyContent).subscribe(reply => {
        const comment = this.comments.find(c => c.id === commentId);
        if (comment) {
          comment.replies.push(reply);
        }
        this.replyContent = '';
        this.replyingToCommentId = null;
      });
    }
  }
  toggleReplyInput(commentId: number) {
    this.replyingToCommentId = this.replyingToCommentId === commentId ? null : commentId;
  }
}
