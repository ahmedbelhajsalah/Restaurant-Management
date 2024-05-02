import { Component } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  constructor(private signupService: AuthService,
    private fb:FormBuilder
  ){}

  signupForm!: FormGroup;


  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.email]],
      password: [null, [Validators.required, Validators.minLength(7)]],
      confirmPassword: [null, [Validators.required, Validators.minLength(7), this.passwordMatchValidator]],
    });
  }

  passwordMatchValidator = (control: FormControl): { [key: string]: boolean } | null => {
    const password = this.signupForm?.controls['password'].value;
    const confirmPassword = control.value;
    if(password && confirmPassword && password !== confirmPassword){
      return { 'passwordMismatch': true };
    }
  
    
    return null;
  }
  

  onSubmit(){
    this.signupService.signUp(this.signupForm.value).subscribe((res) =>{
      console.log(res);
      if(res.id != null){
        alert('signed up successfully');
      }
      else{
        alert('failed to sign up');
      }
    })
  }

}
