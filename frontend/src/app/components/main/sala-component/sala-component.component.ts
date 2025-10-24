import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { Sala } from '../../../models/sala';
import { SalaService } from '../../../services/sala-service';
import { SalaDialog } from '../../dialogs/sala-dialog/sala-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sala-component',
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatToolbarModule, CommonModule],
  templateUrl: './sala-component.component.html',
  styleUrls: ['./sala-component.component.css'],
})
export class SalaComponent implements OnInit {
  displayedColumns = ['id', 'kapacitet', 'brojRedova', 'bioskop', 'actions'];
  dataSource!: MatTableDataSource<Sala>;

  constructor(private salaService: SalaService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.salaService.getAllSalas().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource<Sala>(data);
      },
      error: (error) => console.log(error.message),
    });
  }

  public openDialog(flag: number, id?: number, kapacitet?: number, brojRedova?: number, bioskop?: any): void {
    const ref = this.dialog.open(SalaDialog, { data: { id, kapacitet, brojRedova, bioskop } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) this.loadData();
    });
  }
}
