import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.css'
})
export class UpdateProductComponent implements OnInit {

  
  

  constructor(private fb: FormBuilder, private router: Router, private adminService: AdminService,
    private activatedRouter: ActivatedRoute
  ){}


  productForm!: FormGroup;
  selectedFile!: File ;
  imagePreview!: string | ArrayBuffer | null;
  categoryId: number = this.activatedRouter.snapshot.params['categoryId'];
  existingImage!: string | ArrayBuffer | null;
  productId: number = this.activatedRouter.snapshot.params['productId'];
  imgChanged = false;
  additionalImages: File[] = [];
  additionalImagesPreview: (string | ArrayBuffer| null)[] = [];

  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: [null, [Validators.required]],
      price: [null, [Validators.required, Validators.pattern("^[0-9]*$")]],
      description: [null, [Validators.required]],
      detailedDescription: [null, [Validators.required]],
      img: [null],
    });
    this.getProductById()
  }

  getProductById(){
    this.adminService.getProductById(this.productId).subscribe(data =>{
      const productDto = data;
      this.existingImage = 'data:image/jpeg;base64,' + data.returnedImg;
      this.productForm.patchValue(productDto);
    })
  }

  onFileSelected(event: any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
    this.imgChanged = true;
    this.existingImage = null;
  }

  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }

  updateProduct() {
    const formData: FormData = new FormData();
    if(this.imgChanged){
      formData.append('img', this.selectedFile);
    }
    formData.append('name', this.productForm.get('name')?.value);
    formData.append('price', this.productForm.get('price')?.value);
    formData.append('description', this.productForm.get('description')?.value);
    formData.append('detailedDescription', this.productForm.get('detailedDescription')?.value);
    this.additionalImages.forEach((file, index) => {
      formData.append(`additionalImages[${index}]`, file);
    });
    this.adminService.updateProduct(this.productId,formData).subscribe(data =>{
      alert("Restaurant updated successfully");
    },error => {
      alert("Something went wrong");
    });
   
    }
  
    onAdditionalFilesSelected(event: any): void {
      const files = Array.from(event.target.files) as File[];
      this.additionalImages.push(...files);
      
  
      files.forEach(file => {
        const reader = new FileReader();
        reader.onload = () => {
          this.additionalImagesPreview.push(reader.result);
        };
        reader.readAsDataURL(file);
      });
    }

}
