import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioPagina } from './usuario-pagina';

describe('UsuarioPagina', () => {
  let component: UsuarioPagina;
  let fixture: ComponentFixture<UsuarioPagina>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuarioPagina]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioPagina);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
