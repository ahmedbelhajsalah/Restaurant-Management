import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.css'
})
export class PostProductComponent implements OnInit {

  constructor(private adminService: AdminService){}

  ngOnInit(): void {
    this.getAllCategories();
  }

  getAllCategories(){
    this.adminService.getAllCategories().subscribe(data =>{
      console.log(data)
    })
  }


}
