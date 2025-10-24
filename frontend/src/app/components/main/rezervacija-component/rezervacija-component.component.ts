import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Rezervacija } from '../../../models/rezervacija';
import { RezervacijaService } from '../../../services/rezervacija-service';
import { MatDialog } from '@angular/material/dialog';
import { RezervacijaDialog } from '../../dialogs/rezervacija-dialog/rezervacija-dialog.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-rezervacija-component',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, MatTableModule, DatePipe, CommonModule],
  templateUrl: './rezervacija-component.component.html',
  styleUrls: ['./rezervacija-component.component.css'],
})
export class RezervacijaComponent implements OnInit {
  displayedColumns = ['id', 'brojOsoba', 'cenaKarti', 'datum', 'placeno', 'film', 'sala', 'actions'];
  dataSource!: MatTableDataSource<Rezervacija>;

  constructor(private rezervacijaService: RezervacijaService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.rezervacijaService.getAllRezervacije().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource<Rezervacija>(data);
      },
      error: (error) => console.log(error.message),
    });
  }

  public openDialog(flag: number, rezervacija?: Rezervacija): void {
    const ref = this.dialog.open(RezervacijaDialog, { data: rezervacija || {} });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) this.loadData();
    });
  }
}
