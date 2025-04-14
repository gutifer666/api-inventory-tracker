# Implementación de Autenticación en Angular

Este documento proporciona una guía para implementar la autenticación en tu aplicación Angular que se conecta con la API de Spring Boot.

## 1. Crear un Servicio de Autenticación

Primero, crea un servicio de autenticación en Angular:

```bash
ng generate service services/auth
```

Implementa el servicio de autenticación:

```typescript
// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser') || 'null'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password })
      .pipe(
        tap(user => {
          // Almacenar detalles del usuario y token JWT en localStorage
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }

  logout() {
    // Eliminar usuario del localStorage al cerrar sesión
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!this.currentUserValue;
  }

  getToken(): string | null {
    return this.currentUserValue ? this.currentUserValue.token : null;
  }

  getRoles(): string | null {
    return this.currentUserValue ? this.currentUserValue.roles : null;
  }
}
```

## 2. Crear un Interceptor HTTP para Agregar el Token JWT

```bash
ng generate interceptor interceptors/jwt
```

Implementa el interceptor:

```typescript
// src/app/interceptors/jwt.interceptor.ts
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Obtener el token JWT del servicio de autenticación
    const token = this.authService.getToken();
    
    // Si hay un token, añadirlo al header de autorización
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    
    return next.handle(request);
  }
}
```

## 3. Registrar el Interceptor en el Módulo Principal

```typescript
// src/app/app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JwtInterceptor } from './interceptors/jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    // ... otros componentes
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

## 4. Crear un Componente de Login

```bash
ng generate component components/login
```

Implementa el componente de login:

```typescript
// src/app/components/login/login.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    // Redirigir si ya está logueado
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    }

    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // Getter para acceder fácilmente a los campos del formulario
  get f() { return this.loginForm.controls; }

  onSubmit() {
    // Detener si el formulario es inválido
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authService.login(this.f['username'].value, this.f['password'].value)
      .subscribe({
        next: () => {
          // Navegar a la página principal después del login exitoso
          this.router.navigate(['/']);
        },
        error: error => {
          this.error = error.error?.message || 'Error de autenticación';
          this.loading = false;
        }
      });
  }
}
```

Implementa la plantilla HTML:

```html
<!-- src/app/components/login/login.component.html -->
<div class="login-container">
  <h2>Login</h2>
  <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" formControlName="username" class="form-control" [ngClass]="{ 'is-invalid': f['username'].touched && f['username'].errors }" />
      <div *ngIf="f['username'].touched && f['username'].errors" class="invalid-feedback">
        <div *ngIf="f['username'].errors['required']">Username es requerido</div>
      </div>
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" formControlName="password" class="form-control" [ngClass]="{ 'is-invalid': f['password'].touched && f['password'].errors }" />
      <div *ngIf="f['password'].touched && f['password'].errors" class="invalid-feedback">
        <div *ngIf="f['password'].errors['required']">Password es requerido</div>
      </div>
    </div>
    <div class="form-group">
      <button [disabled]="loading" class="btn btn-primary">
        <span *ngIf="loading" class="spinner-border spinner-border-sm mr-1"></span>
        Login
      </button>
    </div>
    <div *ngIf="error" class="alert alert-danger mt-3">{{error}}</div>
  </form>
</div>
```

## 5. Crear un Guard para Proteger Rutas

```bash
ng generate guard guards/auth
```

Implementa el guard:

```typescript
// src/app/guards/auth.guard.ts
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(
    private router: Router,
    private authService: AuthService
  ) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // Verificar si el usuario está logueado
    if (this.authService.isLoggedIn()) {
      // Verificar si la ruta requiere roles específicos
      const requiredRoles = route.data['roles'] as Array<string>;
      if (requiredRoles) {
        const userRoles = this.authService.getRoles()?.split(', ') || [];
        // Verificar si el usuario tiene alguno de los roles requeridos
        const hasRequiredRole = requiredRoles.some(role => 
          userRoles.includes(role) || userRoles.includes(`ROLE_${role}`)
        );
        
        if (!hasRequiredRole) {
          // Si no tiene los roles requeridos, redirigir a la página de acceso denegado
          this.router.navigate(['/access-denied']);
          return false;
        }
      }
      
      // Usuario logueado y con los roles adecuados
      return true;
    }
    
    // No logueado, redirigir al login
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
```

## 6. Configurar las Rutas

```typescript
// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  // Rutas protegidas con roles específicos
  { 
    path: 'admin', 
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  // Ruta por defecto
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

## 7. Añadir un Componente de Navegación con Opciones de Login/Logout

```typescript
// src/app/components/nav/nav.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {
  
  constructor(
    private router: Router,
    public authService: AuthService
  ) { }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
```

```html
<!-- src/app/components/nav/nav.component.html -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container">
    <a class="navbar-brand" href="#">Inventory Tracker</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link" routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Home</a>
        </li>
        <!-- Mostrar enlaces según el rol -->
        <li class="nav-item" *ngIf="authService.isLoggedIn() && authService.getRoles()?.includes('ROLE_ADMIN')">
          <a class="nav-link" routerLink="/admin" routerLinkActive="active">Admin</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item" *ngIf="!authService.isLoggedIn()">
          <a class="nav-link" routerLink="/login">Login</a>
        </li>
        <li class="nav-item" *ngIf="authService.isLoggedIn()">
          <a class="nav-link" (click)="logout()" style="cursor: pointer;">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
```

## Conclusión

Con estos componentes, servicios y configuraciones, tu aplicación Angular estará lista para autenticarse con la API de Spring Boot usando JWT. Asegúrate de adaptar el código según las necesidades específicas de tu aplicación.

Recuerda que este es solo un ejemplo básico. En una aplicación real, deberías considerar:

1. Manejo de errores más robusto
2. Renovación automática de tokens
3. Almacenamiento seguro de tokens (posiblemente usando cookies HttpOnly)
4. Implementación de logout en el servidor
5. Protección contra CSRF
