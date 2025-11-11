// url=https://github.com/ArquitecturaSoftware-ImagiCode/AgoraSoft/blob/HU-63-RegistroDeUsuariosAplicativoPlazas/frontend/src/app/components/prov-productos/prov-productos.spec.ts
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProvProductos } from './prov-productos';
import { AuthService } from '../../../services/auth.service';
import { of } from 'rxjs';

// Creamos un stub mínimo para AuthService para que la inyección no rompa en tests
class AuthServiceStub {
  async getToken() {
    return null;
  }
}

describe('ProvProductos', () => {
  let component: ProvProductos;
  let fixture: ComponentFixture<ProvProductos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProvProductos],
      providers: [{ provide: AuthService, useClass: AuthServiceStub }],
    }).compileComponents();

    fixture = TestBed.createComponent(ProvProductos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
