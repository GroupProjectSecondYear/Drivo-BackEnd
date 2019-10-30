import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { UserAuthenticationServiceService } from '../user-authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardServiceService implements CanActivate {

  constructor(
    private userAuthenticationService : UserAuthenticationServiceService,
    private router : Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if(this.userAuthenticationService.isUserLoggedIn()){
      return true;
    }
    this.router.navigate(['/']);
    return false;
  }
}
