import { Component, ElementRef, Input, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import KeenSlider, { KeenSliderInstance, KeenSliderPlugin } from "keen-slider";
import { CustomerService } from '../../customer/customer-service/customer.service';
import { ActivatedRoute } from '@angular/router';

function ThumbnailPlugin(main: KeenSliderInstance): KeenSliderPlugin {
  return (slider) => {
    function removeActive() {
      slider.slides.forEach((slide) => {
        slide.classList.remove("active");
      });
    }
    function addActive(idx: number) {
      slider.slides[idx].classList.add("active");
    }

    function addClickEvents() {
      slider.slides.forEach((slide, idx) => {
        slide.addEventListener("click", () => {
          main.moveToIdx(idx);
        });
      });
    }

    slider.on("created", () => {
      if (slider.track.details) {
        addActive(slider.track.details.rel);
      }
      addClickEvents();
      main.on("animationStarted", (main) => {
        removeActive();
        const next = main.animator.targetIdx || 0;
        addActive(main.track.absToRel(next));
        slider.moveToIdx(Math.min(slider.track.details.maxIdx, next));
      });
    });
  };
}

@Component({
  selector: 'app-keen-slider',
  templateUrl: './keen-slider.component.html',
  styleUrls: [
    "../../../../../node_modules/keen-slider/keen-slider.min.css",
    "./keen-slider.component.css",
  ]
})
export class KeenSliderComponent implements OnInit {

  constructor(private activatedRouter: ActivatedRoute,private customerService: CustomerService, private cdr: ChangeDetectorRef) { }

  @ViewChild("sliderRef") sliderRef!: ElementRef<HTMLElement>;
  @ViewChild("thumbnailRef") thumbnailRef!: ElementRef<HTMLElement>;
  productId: any = this.activatedRouter.snapshot.params['productId'];

  slider: KeenSliderInstance | null = null;
  thumbnailSlider: KeenSliderInstance | null = null;
  @Input() productImages: string[] = [];
  countImages: number[] = [];
  productAdiitionalImages: string[] = [];

  ngOnInit(): void {
    this.getProdutById(this.productId);
  }

  getProdutById(productId: number) {
    this.customerService.getProductById(productId).subscribe(
      data => {
        this.productAdiitionalImages = data.returnedAdditionalImages.map((image: any) => 'data:image/jpeg;base64,' + image);
        this.countImages = this.productAdiitionalImages.map((_, index) => index);
        this.initializeSlider();
      }
    );
  }

  initializeSlider() {
    this.cdr.detectChanges();
    if (typeof document !== 'undefined' && this.sliderRef.nativeElement && this.thumbnailRef.nativeElement) {
      this.slider = new KeenSlider(this.sliderRef.nativeElement);
      this.thumbnailSlider = new KeenSlider(
        this.thumbnailRef.nativeElement,
        {
          initial: 0,
          slides: {
            perView: 4,
            spacing: 10,
          },
        },
        [ThumbnailPlugin(this.slider)]
      );
    } else {
      console.error("Slider or thumbnail elements are not available.");
    }
  }
}
