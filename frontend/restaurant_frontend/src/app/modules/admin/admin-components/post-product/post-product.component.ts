import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.css'
})
export class PostProductComponent {

  

  constructor(private fb: FormBuilder, private router: Router, private adminService: AdminService,
    private activatedRouter: ActivatedRoute
  ){}


  productForm!: FormGroup;
  selectedFile!: File ;
  imagePreview!: string | ArrayBuffer | null;
  categoryId: any = this.activatedRouter.snapshot.params['categoryId'];


  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: [null, [Validators.required]],
      price: [null, [Validators.required, Validators.pattern("^[0-9]*$")]],
      description: [null, [Validators.required]],
      img: [null, [Validators.required]],
    });
  }

  onFileSelected(event: any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  onSubmit() {
    const formData: FormData = new FormData();
    formData.append('img', this.selectedFile);
    formData.append('name', this.productForm.get('name')?.value);
    formData.append('price', this.productForm.get('price')?.value);
    formData.append('description', this.productForm.get('description')?.value);
    this.adminService.postProductById(this.categoryId,formData).subscribe(data =>{
      alert("Product posted successfully");
    },error => {
      alert("Something went wrong");
    })
   
    }
  


}
