import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioComponente } from './usuario';

describe('Usuario', () => {
  let component: UsuarioComponente;
  let fixture: ComponentFixture<UsuarioComponente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuarioComponente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioComponente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
