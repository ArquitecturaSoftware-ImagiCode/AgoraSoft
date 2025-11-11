import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EmpleadoPagina } from './empleado-pagina';

describe('EmpleadoPagina', () => {
  let component: EmpleadoPagina;
  let fixture: ComponentFixture<EmpleadoPagina>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpleadoPagina]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpleadoPagina);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
