import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {


  constructor(private fb: FormBuilder, private router: Router, private adminService: AdminService){}


  categoryForm!: FormGroup;
  selectedFile!: File ;
  imagePreview!: string | ArrayBuffer | null;



  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      img: [null],
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
    formData.append('name', this.categoryForm.get('name')?.value);
    formData.append('description', this.categoryForm.get('description')?.value);
    this.adminService.postCategory(formData).subscribe(data =>{
      alert("Car posted successfully");
    },error => {
      alert("Something went wrong");
    })
   
    }
  

}
