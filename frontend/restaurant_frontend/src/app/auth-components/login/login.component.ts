import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService, User } from '../../auth-services/auth-service/auth.service';
import { StorageService } from '../../auth-services/storage-service/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private signupService: AuthService,
    private fb:FormBuilder
  ){}
  signupForm!: FormGroup;

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      email: [null, [Validators.email]],
      password: [null, [Validators.required, Validators.minLength(7)]],
    });
  }


  onSubmit(){
    this.signupService.login(this.signupForm.value).subscribe((res) =>{
      console.log(res);
      if(res.userId != null){
        const user ={
          id: res.userId,
          role: res.userRole
        }
        StorageService.saveToken(res.jwt);
        StorageService.saveUser(user);
      } else{
        console.log('wrong credentials');
      }
      
    })
  }

}
