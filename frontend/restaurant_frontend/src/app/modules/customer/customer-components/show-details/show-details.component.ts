import { ChangeDetectorRef, Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Comment, CustomerService } from '../../customer-service/customer.service';
import { MatDialog } from '@angular/material/dialog';
import { BookDialogComponent } from '../../../common-components/book-dialog/book-dialog.component';
import { StorageService } from '../../../../auth-services/storage-service/storage.service';

@Component({
  selector: 'app-show-details',
  templateUrl: './show-details.component.html',
  styleUrl: './show-details.component.css'
})
export class ShowDetailsComponent implements OnInit {

replyingToCommentId: any;
replyContent: any;

  readonly dialog = inject(MatDialog);
  price: number= 0;
  userId: number = StorageService.getUserId();
  userName: string = '';
  newComment: string = '';

  constructor(private activatedRouter: ActivatedRoute, private customerService: CustomerService, private cdr: ChangeDetectorRef){}
  ngOnInit(): void {
    this.userId = StorageService.getUserId();
    this.getProdutById(this.productId);
    this.fetchComments();
  }

  productId: any = this.activatedRouter.snapshot.params['productId'];
  productName: string= '';
  productAdditionalImages: string[] = [];
  ImagesCount : number[] = [];
  productAdditionalDescription: string ='';
  comments: Comment[] = [];
  rep: any[] =[]

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
    if (this.newComment.trim()) {
      this.customerService.postComment(this.productId, this.userId, this.newComment).subscribe(comment => {
        this.customerService.getUserNameById(comment.user_id).subscribe(userName =>{
          comment.userName = userName.userName;
        });
        this.comments.push(comment);
        this.newComment = '';
      });
    }
  }

  fetchComments() {
    this.customerService.getCommentsByProductId(this.productId).subscribe(comments => {
      this.comments = comments;
      comments.forEach(comment => {
        this.customerService.getUserNameById(comment.user_id).subscribe(userName =>{
          comment.userName = userName.userName;
        });
        this.customerService.getAllReplyByCommentId(comment.id).subscribe(replies => {
          comment.replies = replies;
          if(replies){
            replies.forEach((reply: { id: number; likes: number; userName: string, user_id: number })=>{
              this.customerService.getUserNameById(reply.user_id).subscribe(userName =>{
                reply.userName = userName.userName;
                this.cdr.detectChanges();
              });
              this.customerService.countLikesByReply(reply.id).subscribe(data=>{
                reply.likes = data
              })
            })
          }
        });
        this.customerService.countLikesByComment(comment.id).subscribe(data =>
          comment.likes = data
        );
      });
    });
  }
  
  postReply(commentId: number) {
    if (this.replyContent.trim()) {
      this.customerService.postReply(commentId, this.userId, this.replyContent).subscribe(reply => {
        this.customerService.getUserNameById(reply.user_id).subscribe(userName =>{
          reply.userName = userName.userName;
          this.cdr.detectChanges();
        });
        const comment = this.comments.find(c => c.id === commentId);
        if (comment) {
          if (!comment.replies) {
            comment.replies = []; // Ensure the replies array is initialized
          }
          comment.replies.push(reply); // Update UI without refresh
        }

        this.replyContent = '';
        this.replyingToCommentId = null;
        this.cdr.detectChanges();
      });
    }
  }
  toggleReplyInput(commentId: number) {
    this.replyingToCommentId = this.replyingToCommentId === commentId ? null : commentId;
  }
  likeComment(comment_id: any) {
    this.customerService.isCommentLiked(comment_id, this.userId).subscribe(isLiked => {
      if (isLiked) {
        this.customerService.unlikeComment(comment_id, this.userId).subscribe(() => {
          const comment = this.comments.find(c => c.id === comment_id);
          if (comment) {
            comment.likes--;
          }
        });
      } else {
        this.customerService.commentLike(comment_id, this.userId).subscribe(() => {
          const comment = this.comments.find(c => c.id === comment_id);
          if (comment) {
            comment.likes++;
          }
        });
      }
    });
  }

  likeReply(reply_id: any) {
    this.customerService.isReplyLiked(reply_id, this.userId).subscribe(isLiked => {
      if (isLiked) {
        this.customerService.unlikeReply(reply_id, this.userId).subscribe(() => {
          this.comments.forEach((comment) => {
            const reply = comment.replies.find(r => r.id === reply_id);
            if (reply) {
              reply.likes--;
            }
          });
        });
      } else {
        this.customerService.replyLike(reply_id, this.userId).subscribe(() => {
          this.comments.forEach((comment) => {
            const reply = comment.replies.find(r => r.id === reply_id);
            if (reply) {
              reply.likes++;
            }
          });
        });
      }
    });
  }

  deleteComment(comment_id: number){
    this.customerService.deleteComment(comment_id).subscribe(data =>{
      this.comments = this.comments.filter(comment => comment.id !== comment_id);
    });
  }

  deleteReply(commentId: number, replyId: number) {
    this.customerService.deleteReply(replyId).subscribe(data => {
      const comment = this.comments.find(c => c.id === commentId);
      if (comment) {
        comment.replies = comment.replies.filter(reply => reply.id !== replyId);
      }
    });
  }
  
}
