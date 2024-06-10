import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../admin-services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-category',
  templateUrl: './update-category.component.html',
  styleUrl: './update-category.component.css'
})
export class UpdateCategoryComponent {

  constructor(private fb: FormBuilder, private router: Router, private adminService: AdminService,
    private activatedRouter: ActivatedRoute
  ){}

  categoryForm!: FormGroup;
  selectedFile!: File ;
  imagePreview!: string | ArrayBuffer | null;
  categoryId: number = this.activatedRouter.snapshot.params['categoryId'];
  existingImage!: string | ArrayBuffer | null;
  imgChanged = false;


  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      img: [null],
    });
    this.getCategoryById()
  }

  getCategoryById(){
    this.adminService.getCategoryById(this.categoryId).subscribe(data =>{
      console.log('data',data)
      const categoryDto = data;
      this.existingImage = 'data:image/jpeg;base64,' + data.returnedImg;
      this.categoryForm.patchValue(categoryDto);
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

  updateCategory() {
    const formData: FormData = new FormData();
    if(this.imgChanged){
      formData.append('img', this.selectedFile);
    }
    formData.append('name', this.categoryForm.get('name')?.value);
    formData.append('description', this.categoryForm.get('description')?.value);
    this.adminService.updateCategory(this.categoryId,formData).subscribe(data =>{
      alert("Product posted successfully");
    },error => {
      alert("Something went wrong");
    });
   
    }
  

}
